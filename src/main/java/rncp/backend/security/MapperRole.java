package rncp.backend.security;

public class MapperRole {
    public static String toSecurityRole(String backendRole) {
        if (backendRole == null) return null;

        if (backendRole.equalsIgnoreCase("PROFESSEUR")) {
            return "ROLE_PROF";
        }

        if (backendRole.equalsIgnoreCase("ETUDIANT")) {
            return "ROLE_USER";
        }

        return backendRole;
    }
}
