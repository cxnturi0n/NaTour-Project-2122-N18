package com.cinamidea.natour.report;


import junit.framework.TestCase;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**Sect + Boundary Value testing

 Sect equivalence classes
 *title
  CE1 null -> INVALID
  CE2 between 3 and 32 included -> VALID
  CE3 less than 3 -> INVALID
  CE4 greater than 32 -> INVALID
 *description
  CE1 null -> INVALID
  CE2 between 5 and 255 -> VALID
  CE3 greater than 255 -> INVALID
  CE4 less than 5 -> INVALID

 Bounary value testing
 title_nom = 16
 description_nom = 23

 Testing ..
 title_min, title_min+1, title_nom, title_max-1, title_max with description_nom
 and..
 description_min, description_min+1, description_nom, description_max-1, description_max with title_nom


 */
public class ReportModelTest extends TestCase {

    ReportModel report = new ReportModel();

    //CE1 CE1
    @Test
    public void test_TitleNull_DescriptionNull(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity(null, null));
    }

    //CE1 CE2
    @Test
    public void test_TitleNull_DescriptionBetween5and255(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity(null, "Descrizione"));
    }

    //CE1 CE3
    @Test
    public void test_TitleNull_DescriptionGreaterThan255() {
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity(null, "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem."));
    }

    //CE1 CE4
    @Test
    public void test_TitleNull_DescriptionLessThan5(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity(null, "Desc"));
    }

    //CE2 CE1
    @Test
    public void test_TitleBetween3and32_DescriptionNull(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity("Titolo", null));
    }

    //CE2 CE2
    @Test
    public void test_TitleBetween3and32_DescriptionBetween5and255(){
        assertEquals(true, report.checkReportValidity("Titolo", "Descrizione"));
    }

    //CE2 CE3
    @Test
    public void test_TitleBetween3and32_DescriptionGreaterThan255(){
        assertEquals(false, report.checkReportValidity("Titolo", "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem."));    }

    //CE2 CE4
    @Test
    public void test_TitleBetween3and32_DescriptionLessThan5(){
        assertEquals(false,  report.checkReportValidity("Titolo", "Desc"));
    }

    //CE3 CE1
    @Test
    public void test_TitleLessThan3_DescriptionNull(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity("T", null));
    }

    //CE3 CE2
    @Test
    public void test_TitleLessThan3_DescriptionBetween5and255(){
        assertEquals(false, report.checkReportValidity("T", "Descrizione"));
    }

    //CE3 CE3
    @Test
    public void test_TitleLessThan3_DescriptionGreaterThan255(){
        assertEquals(false, report.checkReportValidity("T", "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem."));
    }

    //CE3 CE4
    @Test
    public void test_TitleLessThan3_DescriptionLessThan5(){
        assertEquals(false,  report.checkReportValidity("T", "Desc"));
    }

    //CE4 CE1
    @Test
    public void test_TitleGreaterThan32_DescriptionNull(){
        Assertions.assertThrows(NullPointerException.class, () -> report.checkReportValidity("Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur.", null));
    }

    //CE4 CE2
    @Test
    public void test_TitleGreaterThan32_DescriptionBetween5and255(){
        assertEquals(false, report.checkReportValidity("Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur.", "Descrizione"));
    }

    //CE4 CE3
    @Test
    public void test_TitleGreaterThan32_DescriptionGreaterThan255(){
        assertEquals(false, report.checkReportValidity("Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur.", "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem."));
    }

    //CE4 CE4
    @Test
    public void test_TitleGreaterThan32_DescriptionLessThan5(){
        assertEquals(false, report.checkReportValidity("Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur.", "Desc"));
    }

    //Boundary Value analysis

    //Test title.length()==3; description.length()==23
    @Test
    public void test_TitleMin_DescriptionNom(){
        assertEquals(true, report.checkReportValidity("TTT", " Descrizione itinerario."));
    }

    //Test title.length()==4; description.length()==23
    @Test
    public void test_TitleMinPlus_DescriptionNom(){
        assertEquals(true, report.checkReportValidity("Titl", " Descrizione itinerario."));
    }

    //Test title.length()==16; description.length()==23
    @Test
    public void test_TitleNom_DescriptionNom(){
        assertEquals(true, report.checkReportValidity("Titolo migliore.", " Descrizione itinerario."));
    }

    //Test title.length()==31; description.length()==23
    @Test
    public void test_TitleMaxMinus_DescriptionNom(){
        assertEquals(true, report.checkReportValidity("Titolo migliore Titolo migliore", " Descrizione itinerario."));
    }

    //Test title.length()==32; description.length()==23
    @Test
    public void test_TitleMax_DescriptionNom(){
        assertEquals(true, report.checkReportValidity("Titolo migliore Titolo migliore.", " Descrizione itinerario."));
    }

    //Test title.length()==16; description.length()==5
    @Test
    public void test_TitleNom_DescriptionMin(){
        assertEquals(true, report.checkReportValidity("Titolo migliore.", "Descr"));
    }

    //Test title.length()==16; description.length()==6
    @Test
    public void test_TitleNom_DescriptionMinPlus(){
        assertEquals(true, report.checkReportValidity("Titolo migliore.", "Descri"));
    }

    //Test title.length()==16; description.length()==254
    @Test
    public void test_TitleNom_DescriptionMaxMinus(){
        assertEquals(true, report.checkReportValidity("Titolo migliore.", "d8qmeC1PxwD311YHX6vTQFHNUPESFcvn8ulErWpAn4sxrzCtyustmgJ29OaCUGHwldrHv3oxO7WM55cOrmOAgjQlsLn2rG2L1aYvp7AJUQNlgEeyKsFk2R1mSPuWQ9o4xvARUMz8UALHNDHvb70oG7n9JiNxiueDWa6hGM6qARoxRUvZz4nRqHMvvTIUFidLZjVgC7KG3LFlDFioOMOKcBrjvf0zTFykzNbL4lKgky6qEMCAF5LTnvqCkQXkLQ"));
    }

    //Test title.length()==16; description.length()==255
    @Test
    public void test_TitleNom_DescriptionMax(){
        assertEquals(true, report.checkReportValidity("Titolo migliore.", "d8qfmeC1PxwD311YHX6vTQFHNUPESFcvn8ulErWpAn4sxrzCtyustmgJ29OaCUGHwldrHv3oxO7WM55cOrmOAgjQlsLn2rG2L1aYvp7AJUQNlgEeyKsFk2R1mSPuWQ9o4xvARUMz8UALHNDHvb70oG7n9JiNxiueDWa6hGM6qARoxRUvZz4nRqHMvvTIUFidLZjVgC7KG3LFlDFioOMOKcBrjvf0zTFykzNbL4lKgky6qEMCAF5LTnvqCkQXkLQ"));

    }

}