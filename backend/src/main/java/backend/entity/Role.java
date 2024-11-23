package backend.entity;

public enum Role {
	STUDENT("student"), ADMIN("admin"), TEACHER("teacher");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

