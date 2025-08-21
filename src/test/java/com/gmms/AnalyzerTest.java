package com.gmms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerTest {

  @Test
  void testBuildSyntacticTreeWithValidJson() throws Exception {
    String jsonInput = """
        {
          "tokens": [
            {
              "text": { "content": "Hello", "beginOffset": 0 },
              "partOfSpeech": { "tag": "INTJ" },
              "dependencyEdge": { "headTokenIndex": 0, "label": "ROOT" },
              "lemma": "hello"
            },
            {
              "text": { "content": "world", "beginOffset": 5 },
              "partOfSpeech": { "tag": "NOUN" },
              "dependencyEdge": { "headTokenIndex": 0, "label": "dobj" },
              "lemma": "world"
            }
          ]
        }
        """;

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);

    assertNotNull(root, "La radice non dovrebbe essere null");
    assertEquals("Hello", root.getText(), "La radice dovrebbe essere 'Hello'");
    assertEquals("ROOT", root.getDependencyLabel(), "La radice dovrebbe avere label ROOT");
    assertEquals("INTJ", root.getPartOfSpeech(), "POS della radice inatteso");
    assertEquals("hello", root.getLemma(), "Lemma della radice inatteso");

    assertFalse(root.getnode().isEmpty(), "La radice dovrebbe avere almeno un figlio");
    SyntacticNode child = root.getnode().get(0);
    assertEquals("world", child.getText(), "Il figlio dovrebbe essere 'world'");
    assertEquals("dobj", child.getDependencyLabel(), "Il figlio dovrebbe avere label 'dobj'");
    assertEquals("NOUN", child.getPartOfSpeech(), "POS del figlio inatteso");
    assertEquals("world", child.getLemma(), "Lemma del figlio inatteso");

    // Check stampa albero (formato generale, non exact match)
    String printed = root.toString();
    assertTrue(printed.contains("Hello [INTJ | ROOT]"));
    assertTrue(printed.contains("world [NOUN | dobj]"));
  }

  @Test
  void testBuildSyntacticTreeWithEmptyTokens() throws Exception {
    String jsonInput = """
        {
          "tokens": []
        }
        """;

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);
    assertNull(root, "Con tokens vuoti il risultato deve essere null");
  }

  @Test
  void testBuildSyntacticTreeWithMissingRoot() throws Exception {
    String jsonInput = """
        {
          "tokens": [
            {
              "text": { "content": "hello", "beginOffset": 0 },
              "partOfSpeech": { "tag": "INTJ" },
              "dependencyEdge": { "headTokenIndex": 0, "label": "dep" },
              "lemma": "hello"
            }
          ]
        }
        """;

    SyntacticNode root = Analyzer.buildSyntacticTree(jsonInput);
    assertNull(root, "Se manca un ROOT esplicito, la radice deve essere null");
  }
}
