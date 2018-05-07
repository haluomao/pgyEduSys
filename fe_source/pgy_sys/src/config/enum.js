var RoleEnum = {
	ADMIN: "ADMIN",
	TEACHER: "TEACHER",
	USER: "USER",
	prop: {
		"ADMIN": {code: "ADMIN", value: 2, text: "管理员"},
		"TEACHER": {code: "TEACHER", value: 3, text: "老师"},
		"USER": {code: "USER", value: 4, text: "家长"}
	}
}
// var myRole = RoleEnum.ADMIN;
// var myCode = RoleEnum.prop[myRole].code; // myCode == "teacher"

export { RoleEnum }