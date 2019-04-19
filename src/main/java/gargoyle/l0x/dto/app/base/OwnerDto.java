package gargoyle.l0x.dto.app.base;

import gargoyle.l0x.dto.user.UserDto;

public interface OwnerDto {
    UserDto getAuthor();

    void setAuthor(UserDto author);
}
