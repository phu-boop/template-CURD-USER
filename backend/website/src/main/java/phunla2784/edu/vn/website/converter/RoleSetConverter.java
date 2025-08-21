package phunla2784.edu.vn.website.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;
import phunla2784.edu.vn.website.enums.Role;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Converter
public class RoleSetConverter implements AttributeConverter<Set<Role>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) return "";
        return roles.stream()
                .map(Role::name)      // Enum -> String
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return Set.of();
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
