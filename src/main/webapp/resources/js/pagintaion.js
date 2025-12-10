document.addEventListener('DOMContentLoaded', () => {
	
	const searchForm = document.getElementById('searchForm')
	const pageInput = document.getElementById('pageInput')
	const sizeInput = document.getElementById('sizeInput')
	const sizeSelect = document.getElementById('sizeSelect')
	
	if(searchForm && pageInput && sizeInput) {
		
		// Size select change
		sizeSelect.addEventListener('change', () => {
			sizeInput.value = sizeSelect.value
			pageInput.value = '0'
			searchForm.submit()
			
		})
	}
})