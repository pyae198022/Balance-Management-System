document.addEventListener('DOMContentLoaded', () => {
	
	const profileImage = document.getElementById('profileImage')
	const profileInput = document.getElementById('profileInput')
	const profileButton = document.getElementById('profileButton')
	
	if(profileButton && profileInput &&  profileImage) {
		profileButton.addEventListener('click', () => {
			profileInput.click()
		})
		
		profileInput.addEventListener('change', () => {
			profileImage.submit()
		})
	}
})