package uz.BTService.btservice.common.util;

public enum CategoryEnum {

    IIO_CITIZEN(1L),
    PROF_CITIZEN(2L),
    STATEMENT(3L),
    CAUGHT_WANTED_CITIZEN(4L),
    CAUGHT_LOST_CITIZEN(5L),
    TOTAL_CHECKED_OBJECT_GUARDS(6L),
    CHECKED_HUNTING_WEAPONS(7L);

    private final Long value;

    CategoryEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }


}
