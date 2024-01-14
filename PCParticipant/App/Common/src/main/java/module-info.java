module Common {
    requires com.google.gson;

    opens Dtos to com.google.gson;

    exports Dtos;
    exports Dtos.CustomClasses;
    exports Enums;
    exports Service;
    opens Service to com.google.gson;
    opens Dtos.CustomClasses to com.google.gson;
}