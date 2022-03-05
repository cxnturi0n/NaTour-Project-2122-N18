package org.natour.daosimpl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.natour.entities.QueryFilters;


public class RouteDAOMySqlTest extends TestCase {

    private static RouteDAOMySql route_dao = new RouteDAOMySql();

    /**
     192 sect test cases for getFilteredSql method

     Equivalence classes
     *route_name
     CE1 null -> INVALID
     CE2 empty -> INVALID
     CE3 not null -> VALID
     *levels
     CE1 null -> INVALID
     CE2 empty -> VALID
     CE3 one element -> VALID
     CE4 more than one element -> VALID
     *duration
     CE1 positive -> VALID
     CE2 negative -> INVALID
     *disability
     CE1 true -> VALID
     CE2 false -> VALID
     *tags
     CE1 null -> INVALID
     CE2 empty -> VALID
     CE3 one element -> VALID
     CE4 more than one element -> VALID

     **/
    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, null, 12.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }


    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, null, 167.2f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, null, 7.7f, true, "mountain");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, null, 43.34f, true, "sea;snow");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, null, 90.6f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, null, 12.6f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, null, 76.01f, false, "mountain");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, null, 7.01f, false, "mountain;funny;lake;snow");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, null, -12.01f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }


    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, null, -52.01f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, null, -56.01f, true, "snow");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, null, -2.01f, true, "snow;lake;scary");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, null, -12542.01f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, null, -12542.01f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, null, -12542.01f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", null, -12542.01f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "", +90f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE duration >= 90.0 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));

    }
    @Test
    public void test_RouteNameNull_LevelsNull_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, null, -90f, false, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));

    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "", 120f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));


    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "", 15, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));


    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "", 12.3f, true, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "", 12.3f, true, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "", 12.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "", 12.3f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "", 15f, false, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "", 12.3f, false, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

//Presi fin qui per la doc
@Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, true, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, true, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "", -14f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "", -14f, false, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "", -12.3f, false, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Easy", 20f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Medium", 12.3f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Medium", 14.3f, true, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Hard", 15f, true, "tags1;tags2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Hard", 16.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Medium", 12.3f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy", 15.3f, false, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Medium", 18f, false, "tags1;tags2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Easy", -122.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Medium", -33.3f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Medium", -12.3f, true, "tags1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy", -90.3f, true, "tags1;tags2;tags3;tag4;tag5");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Medium", -17.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Easy", -17.3f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy", -17.3f, false, "tags");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Medium", -17.3f, false, "tag1;tag2;tag1;tag2;tag1;tag2;tag1;tag2;tag1;tag2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Medium;Easy;Extreme", 19.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Medium;Easy;Extreme", 17f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Medium;Easy;Extreme", 27.3f, true, "tag");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }


    //52
    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme;Medium", +123.0f, true, "snow;mountain");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme", +12.0f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme", +12.0f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme;Hard", +17.5f, false, "scary");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme;Hard", +17.5f, false, "scary;tag2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Easy;Hard", -166.5f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme", -166.5f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme", -166.5f, true, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme", -92.01f, true, "tag1;tag2;tag3;tag4");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme;Hard", -122.01f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters(null, "Easy;Extreme;Hard", -1278.01f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Hard;Medium;Extreme", -1f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNull_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters(null, "Easy;Hard;Medium", -1.123f, false, "tag1;tag2;tag3;tag4;tag5");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters(null, null, +154.123f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name", null, +14.23f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name", null, +6543.23f, true, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, +43.265f, true, "tag1;tag2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", null, +87.878f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", null, +123.54f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, +98.66f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, +1.66f, false, "tag1;tag2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", null, -1.66f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", null, -1123f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, -1123f, true, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, -13f, true, "tag1;tag2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", null, -763f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", null, -123.34f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, -76.34f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", null, -543.89f, false, "tag1;tag2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "", +123.89f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "", +983.89f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", +663.69f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", +123.69f, true, "tag1;tag2;tag3");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "", +667.123f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "", +123f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", +987.34f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", +236.34f, false, "tag1;tag2;tag3");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "", -1.34f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsEmpty() {

        QueryFilters filters = new QueryFilters("name1", "", -1534.1451f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", -1534.1451f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", -98.51f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "", -76.091f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "", -123.091f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", -654.091f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "", -654.091f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    //96
    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }
    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Extreme", 27.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, false, "tag1;tag2;tag3");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Hard", 27.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -28.3f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -28.3f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", 28.3f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -23.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -28.3f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -28.3f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy", -28.3f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Hard;Medium", 27.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Extreme", 28.3f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }
    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", 28.3f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", 28.3f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Hard", 27.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", 28.3f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Extreme", 500.3f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", 28.3f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Hard;Medium", -21.3f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Medium", -28.3f, true, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Medium", -28.3f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Hard", -137.7f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }
    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", -17.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", -28.3f, false, "");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Easy;Medium", -28.3f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameNotEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("name1", "Hard;Medium", -28.3f, false, "tag1;tag2;tag3");
        assertEquals("SELECT * FROM Routes WHERE name like '%name1%'", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", null, 30.1f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", null, 17.3f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", null, 27.3f, true, "tag");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", null, 14.3f, true, "tag1;tag2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", null, 27.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", null, 89f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", null, 27.3f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }
    @Test
    public void test_RouteNameEmpty_LevelsNull_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", null, 127f, false, "tag1;tag2;tag3;tag4;tag5");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    //138 in poi

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", null, -654.091f, true, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", null, -123.091f, true, "tag");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", null, -543.091f, true, "tag;tag1;tag2");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", null, -543.091f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", null, -34f, false, "");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", null, -34f, false, "tag1");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsNull_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", null, -34f, false, "tag1;tag2;tag3");
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "", +1234f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "", +14f, true, "");
        assertEquals("SELECT * FROM Routes WHERE duration >= 14.0 AND disability_access = true", route_dao.getFilteredSql(filters));
    }


    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "", +123f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE duration >= 123.0 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "", +123f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE duration >= 123.0 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "", +7563f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "", +73f, false, "");
        assertEquals("SELECT * FROM Routes WHERE duration >= 73.0", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "", +68.23f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "", 68.23f, true, "");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23 AND disability_access = true", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, false, "");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "", -68.23f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE duration >= 68.23 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsEmpty_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "", -123.23f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE duration >= 123.23 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Easy", +123.23f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Easy", +123.23f, true, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Easy\") AND duration >= 123.23 AND disability_access = true", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Easy", +123.23f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Easy\") AND duration >= 123.23 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Easy", +123.23f, true, "tag1;tag2;tag3");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Easy\") AND duration >= 123.23 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%' OR tags like '%tag3%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Easy", +123.23f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }


    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard", +123.23f, false, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard", +123.23f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard", +123.23f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, true, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23 AND disability_access = true", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Easy", -123.23f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Easy\") AND duration >= 123.23 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Easy", -123.23f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Easy\") AND duration >= 123.23 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, false, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard", -123.23f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\") AND duration >= 123.23 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 65.23f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 65.23f, true, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 65.23 AND disability_access = true", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 1267.3 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 1267.3 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, false, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 1267.3", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 1267.3 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_PositiveDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", 1267.3f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 1267.3 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", -666f, true, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", -666f, true, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 666.0 AND disability_access = true", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium", -666f, true, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\") AND duration >= 666.0 AND disability_access = true AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityTrue_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium;Easy", -666f, true, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\" OR level = \"Easy\") AND duration >= 666.0 AND disability_access = true AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsNull() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium;Hard", -666f, false, null);
        Assertions.assertThrows(NullPointerException.class, () -> route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsEmpty() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium;Easy", -666f, false, "");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\" OR level = \"Easy\") AND duration >= 666.0", route_dao.getFilteredSql(filters));

    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium;Easy", -666f, false, "tag1");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\" OR level = \"Easy\") AND duration >= 666.0 AND (tags like '%tag1%')", route_dao.getFilteredSql(filters));
    }

    @Test
    public void test_RouteNameEmpty_LevelsMoreThanOneElement_NegativeDuration_DisabilityFalse_TagsMoreThanOneElement() {
        QueryFilters filters = new QueryFilters("", "Hard;Medium;Easy", -666f, false, "tag1;tag2");
        assertEquals("SELECT * FROM Routes WHERE (level = \"Hard\" OR level = \"Medium\" OR level = \"Easy\") AND duration >= 666.0 AND (tags like '%tag1%' OR tags like '%tag2%')", route_dao.getFilteredSql(filters));
    }

}