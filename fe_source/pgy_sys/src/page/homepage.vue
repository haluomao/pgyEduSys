<template>
	<div id="homepage">
	</div>
</template>

<script>
export default{
	name: 'homepage',
	data() {
		return {
			showGrade: 'this.showGrade'
		}
	},
	props: {
		//showGrade: Boolean
	},
	computed: {
		showGrade2: function(){
			this.showGrade = false;
		} 
	}
}
</script>

<style scoped>

 .centerText {
 	padding-right: 0px;
 	padding-left: 0px;

 }
</style>
