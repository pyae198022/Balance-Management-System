document.addEventListener('DOMContentLoaded', () => {
	const signoutForm = document.getElementById('signoutForm')
	const signoutMenu = document.getElementById('signOutMenu')
	
	signoutMenu.addEventListener('click', (event) => {
		event.preventDefault()
		signoutForm.submit()
	})
})