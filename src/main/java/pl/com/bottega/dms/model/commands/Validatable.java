package pl.com.bottega.dms.model.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public interface Validatable {

    void validate(ValidationErrors errors);

    default boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    class ValidationErrors {

        private Map<String, Set<String>> errors = new HashMap<>();

        public void add(String fieldName, String errorMessage) {
            Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
            fieldErrors.add(errorMessage);
            errors.putIfAbsent(fieldName, fieldErrors);
        }

        public boolean isValid() {
            return errors.isEmpty();
        }

        public Map<String, Set<String>> getErrors() {
            return errors;
        }
    }

}
