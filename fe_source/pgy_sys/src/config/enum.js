var RoleEnum = {
	SUPERADMIN: "SUPERADMIN",
	ADMIN: "ADMIN",
	TEACHER: "TEACHER",
	USER: "USER",
	prop: {
		"SUPERADMIN": {code: "SUPERADMIN", value: 1, text: "超级管理员", children:["ADMIN"]},
		"ADMIN": {code: "ADMIN", value: 2, text: "管理员", children:["TEACHER"]},
		"TEACHER": {code: "TEACHER", value: 3, text: "老师", children:["USER"]},
		"USER": {code: "USER", value: 4, text: "家长", children:[]}
	}
}
// var myRole = RoleEnum.ADMIN;
// var myCode = RoleEnum.prop[myRole].code; // myCode == "teacher"

var MaterialTypeEnum = {
	COURSEWARE: "COURSEWARE",
	CLASSIC: "CLASSIC",
	EXERCISE: "EXERCISE",
	prop: {
		"COURSEWARE": {code: "COURSEWARE", value: 1, text: "互动课件"},
		"CLASSIC": {code: "CLASSIC", value: 2, text: "经典教案"},
		"EXERCISE": {code: "EXERCISE", value: 3, text: "习题"}
	}
}

var AccountStatusEnum = {
	ENABLED: "ENABLED",
	DISABLED: "DISABLED",
	EXPIRED: "EXPIRED",
	prop: {
		"ENABLED": {code: "ENABLED", value: 1, text: "启用"},
		"DISABLED": {code: "DISABLED", value: 2, text: "禁用"},
		"EXPIRED": {code: "EXPIRED", value: 3, text: "已过期"}
	}
}

export { RoleEnum, MaterialTypeEnum, AccountStatusEnum }