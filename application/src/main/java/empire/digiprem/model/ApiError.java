package empire.digiprem.model;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private int status;
    private String message;
    private String path;
    private Instant timestamp = Instant.now();
    private List<ValidationFieldError> errors;

    // Getters & Setters


    public static class ValidationFieldError {
        private String field;
        private String message;

        // ✅ Constructeur vide requis par Jackson
        public ValidationFieldError() {
        }

        // ✅ Constructeur utile pour remplir rapidement les erreurs
        public ValidationFieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        // ✅ Getters & Setters
        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<ValidationFieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationFieldError> errors) {
        this.errors = errors;
    }
}

