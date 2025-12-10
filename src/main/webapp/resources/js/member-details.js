document.addEventListener('DOMContentLoaded', () => {
	const statusChangeDialog = new bootstrap.Modal('#statusChangeDialog')
	const statusChangeButton = document.getElementById('statusChangeButton')

	if(statusChangeButton && statusChangeDialog) {
		statusChangeButton.addEventListener('click', () => {
			statusChangeDialog.show()
		})
	}	
})
