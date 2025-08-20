package com.gmms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TemplateControllerTest {
    
    @Test
    @DisplayName("Deve fornire le informazioni del template")
    void templateController_test_1 () {
        Template template = Template.create("Il [NOUN] [ADJECTIVE] ma solo il [NOUN] [VERB] [NOUN]");
        template.templateWords = new int[] {3, 1, 1};

        
    }
}
