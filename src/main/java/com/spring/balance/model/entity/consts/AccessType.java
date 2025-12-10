package com.spring.balance.model.entity.consts;

public enum AccessType {

	Login, Logout, Signup {

		@Override
		public String getDisplayName() {
			return "Sign Up";
		}
		
		
	}, Timeout;
	
	public String getDisplayName() {
		return this.name();
	}
}
