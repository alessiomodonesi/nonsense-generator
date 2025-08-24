package com.gmms.web;

import com.gmms.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nonsense") // <— prefisso unico
public class WebController {

    @GetMapping("") // GET /nonsense
    public String index(Model model,
            @RequestParam(value = "err", required = false) String err) {
        model.addAttribute("form", new InputForm());
        if (err != null)
            model.addAttribute("error", err);
        return "index";
    }

    @PostMapping("/process") // POST /nonsense/process
    public String process(@ModelAttribute("form") InputForm form, Model model, RedirectAttributes ra) throws Exception {
        String sentence = form.getSentence() == null ? "" : form.getSentence().trim();
        if (!Validator.getInstance().verifySentence(sentence)) {
            model.addAttribute("error", "Input non valido. Inserisci una frase corretta.");
            return "index";
        }

        SentenceController sc = SentenceController.getInstance();
        TemplateController tc = TemplateController.getInstance();
        WordPicker wp = WordPicker.getInstance();

        sc.createSentence(sentence);
        model.addAttribute("input", sentence);
        sc.analysisProcess();
        if (!sc.validationProcess()) {
            model.addAttribute("error", "Validazione fallita. Inserisci un'altra frase.");
            return "index";
        }

        if (form.isShowTree())
            model.addAttribute("syntacticTree", sc.getSyntacticTree());

        model.addAttribute("showTree", form.isShowTree());
        tc.generateTemplate();
        model.addAttribute("template", tc.getTemplateDesc());
        model.addAttribute("wordCount", Arrays.toString(tc.getWordCount()));

        try {
            wp.startWordsExtraction();
            Map<String, List<String>> chosenSnapshot = wp.getWords().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> List.copyOf(e.getValue()), // copia immutabile della lista
                            (a, b) -> a,
                            LinkedHashMap::new // mantieni l’ordine
                    ));
            model.addAttribute("chosenWords", chosenSnapshot);
            sc.generateSentence();
        } catch (RetryInputException e) {
            sc.resetSentenceState();
            wp.resetNumOfRetries();
            model.addAttribute("error", e.getMessage());
            return "index";
        }

        boolean toxicityOk = sc.toxicityProcess();
        model.addAttribute("toxicityOk", toxicityOk);

        if (toxicityOk) {
            model.addAttribute("generatedSentence", sc.getSentenceDesc());
            model.addAttribute("toxicityDetails", Validator.getInstance().getToxicityDetails());
        } else {
            ra.addFlashAttribute("error", "Tossicità troppo alta, riprova");
            return "redirect:/nonsense"; // URL pulito
        }

        return "result";
    }

    // se qualcuno va su /process senza prefisso,
    // lo porto alla home corretta
    @GetMapping("/process")
    public String getProcessDirect() {
        return "redirect:/nonsense";
    }

    // address: / → redirect alla home /nonsense
    @GetMapping(path = { "/", "" }, params = "redirect")
    public String rootRedirect() {
        return "redirect:/nonsense";
    }

    // redirect in caso di tossicità troppo elevata
    @GetMapping("/retry")
    public String retry(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Tossicità troppo alta, riprova");
        return "redirect:/nonsense";
    }

    public static class InputForm {
        private String sentence;
        private boolean showTree;

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public boolean isShowTree() {
            return showTree;
        }

        public void setShowTree(boolean showTree) {
            this.showTree = showTree;
        }
    }
}