package com.spring.balance.controller.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.spring.balance.model.entity.Member;

public record MemberProfileDetails(
		long id,
		String name,
		String phone,
		String email,
		String profileImage,
		String address,
		LocalDateTime registeredAt,
		LocalDateTime lastAccessedAt
		) {
	
	public static MemberProfileDetails from(Member entity) {
		
		var builder = new Builder();
		builder.id(entity.getId())
        .name(entity.getName())
        .profileImage(Optional.ofNullable(entity.getProfileImage()).orElse("defaultImage.jpg"))
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .registeredAt(entity.getActivity().getRegisteredAt())
        .lastAccessedAt(entity.getActivity().getLastAccessAt());
       
		
		var list = List.of(
				Optional.ofNullable(entity.getAddress()).orElse(""), 
				Optional.ofNullable(entity.getTownship()).map(a -> a.getName()).orElse(""),
				Optional.ofNullable(entity.getTownship()).map(a -> a.getDistrict()).map(a -> a.getName()).orElse(""));
		
		var address = list.stream().filter(StringUtils::hasLength)
				.collect(Collectors.joining(", "));
		builder.address(address);
		
		
		return builder.build();
	}
	
	
	public static class Builder {
        private long id;
        private String name;
        private String phone;
        private String email;
        private String profileImage;
        private String address;
        private LocalDateTime registeredAt;
        private LocalDateTime lastAccessedAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder registeredAt(LocalDateTime registeredAt) {
            this.registeredAt = registeredAt;
            return this;
        }

        public Builder lastAccessedAt(LocalDateTime lastAccessedAt) {
            this.lastAccessedAt = lastAccessedAt;
            return this;
        }

        public MemberProfileDetails build() {
            return new MemberProfileDetails(
                    id,
                    name,
                    phone,
                    email,
                    profileImage,
                    address,
                    registeredAt,
                    lastAccessedAt
            );
        }
    }
}
