package phunla2784.edu.vn.website.mapper;

import org.mapstruct.*;
import phunla2784.edu.vn.website.dto.request.UserRequest;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequesttoUser(UserRequest userRequest);
    UserRespond usertoUserRespond(User user);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birthday", ignore = true)
    void updateUserFromRequest(UserRequest request, @MappingTarget User user);
}
