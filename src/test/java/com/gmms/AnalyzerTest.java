package com.gmms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class AnalyzerTest {

  @Test
  @DisplayName("Verifica la creazione del SyntacticTree con un JSON valido")
  void testBuildSyntacticTreeWithValidJson() throws Exception {
    String jsonInput = """
            {
              "tokens": [
                {
                  "text": {"content": "Mangio", "beginOffset": 0},
                  "partOfSpeech": {"tag": "VERB"},
                  "dependencyEdge": {"headTokenIndex": 0, "label": "ROOT"},
                  "lemma": "mangiare"
                },
                {
                  "text": {"content": "una", "beginOffset": 6},
                  "partOfSpeech": {"tag": "DET"},
                  "dependencyEdge": {"headTokenIndex": 2, "label": "DET"},
                  "lemma": "una"
                },
                {
                  "text": {"content": "pizza", "beginOffset": 10},
                  "partOfSpeech": {"tag": "NOUN"},
                  "dependencyEdge": {"headTokenIndex": 0, "label": "OBJ"},
                  "lemma": "pizza"
                }
              ]
            }
        """;

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);

    // Verifica che la radice non sia null e abbia il testo giusto
    assertNotNull(root);
    assertEquals("Mangio", root.getText());
    assertEquals("ROOT", root.getDependencyLabel());

    // Verifica che ci siano figli
    assertFalse(root.getnode().isEmpty());

    // Verifica che uno dei figli sia "pizza"
    boolean hasPizza = root.getnode().stream()
        .anyMatch(child -> "pizza".equals(child.getText()));
    assertTrue(hasPizza);
  }

  @Test
  @DisplayName("Verifica la creazione del SyntacticTree con un JSON senza tokens")
  void testBuildSyntacticTreeWithEmptyTokens() throws Exception {
    String jsonInput = """
            {
              "tokens": []
            }
        """;

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);
    assertNull(root);
  }

  @Test
  @DisplayName("Verifica la creazione del SyntacticTree con un JSON vuoto")
  void testBuildSyntacticTreeWithMissingTokensField() throws Exception {
    String jsonInput = "{}";

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);
    assertNull(root);
  }
}
