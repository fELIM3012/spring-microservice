package spring.microservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.microservice.dto.request.CreateUserRequest;
import spring.microservice.dto.request.LoginRequest;
import spring.microservice.dto.response.GetUserResponse;
import spring.microservice.entities.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public User map(CreateUserRequest createUserRequest);

    public GetUserResponse map(User user);

}
