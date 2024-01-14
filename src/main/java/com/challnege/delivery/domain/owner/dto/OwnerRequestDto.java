package com.challnege.delivery.domain.owner.dto;

import com.challnege.delivery.domain.member.entity.Role;
import com.challnege.delivery.domain.owner.entity.Owner;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class OwnerRequestDto {

    @Email
    @NonNull
    private String email;
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    private String password;
    @NonNull
    private String ownerName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String address;
    @NonNull
    private Role role;

    public Owner toEntity() {
        return Owner.builder()
                .email(this.email)
                .password(this.password)
                .ownerName(this.ownerName)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .role(this.role)
                .build();
    }
}
