package com.gmms.web;

import com.gmms.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public String process(@ModelAttribute("form") InputForm form, Model model) throws Exception {
        String sentence = form.getSentence() == null ? "" : form.getSentence().trim();
        if (!Validator.verifySentence(sentence)) {
            model.addAttribute("error", "Input non valido. Inserisci una frase corretta.");
            return "index";
        }

        SentenceController sc = SentenceController.getInstance();
        TemplateController tc = TemplateController.getInstance();
        WordPicker wp = WordPicker.getInstance();

        sc.createSentence(sentence);
        sc.analysisProcess();
        if (!sc.validationProcess()) {
            model.addAttribute("error", "Validazione fallita. Inserisci un'altra frase.");
            return "index";
        }

        if (form.isShowTree()) {
            // usa un tuo getter disponibile, qui d'esempio:
            model.addAttribute("syntacticTree", sc.getSyntacticTree());
        }

        tc.generateTemplate();
        model.addAttribute("template", tc.getTemplateDesc());
        model.addAttribute("wordCount", Arrays.toString(tc.getWordCount()));

        try {
            wp.startWordsExtraction();
            sc.generateSentence();
        } catch (RetryInputException e) {
            sc.resetSentenceState();
            wp.resetNumOfRetries();
            model.addAttribute("error", e.getMessage());
            return "index";
        }

        boolean toxicityOk = sc.toxicityProcess();
        model.addAttribute("toxicityOk", toxicityOk);
        model.addAttribute("generatedSentence", sc.getSentenceDesc());
        model.addAttribute("input", sentence);
        model.addAttribute("showTree", form.isShowTree());
        return "result";
    }

    // Comodo: se qualcuno va su /process senza prefisso, lo porto alla home
    // corretta
    @GetMapping("/process")
    public String getProcessDirect() {
        return "redirect:/nonsense";
    }

    // Ancora più comodo: / → redirect alla home /nonsense
    @GetMapping(path = { "/", "" }, params = "redirect")
    public String rootRedirect() {
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