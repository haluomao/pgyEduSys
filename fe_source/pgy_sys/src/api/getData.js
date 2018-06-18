// Use mock, (goto main.js first) remove the // below two lines. Then restart.
// import {_fetch} from '@/config/api'
// let fetch = _fetch

import fetch from '@/config/fetch'

//export const login = data => fetch('/admin/login', data, 'POST');

export const login = data => fetch('/api/pgy/v1/auth/login', data, 'POST', 'login');
export const logout = data => fetch('/api/pgy/v1/auth/logout', data);
export const checkLogin = data => fetch('/api/v1/auth/logined', data);

export const upload = data => fetch('/api/v1/upload', data);

export const allCategories = data => fetch('/api/v1/category/all', data);
export const listCategories = data => fetch('/api/v1/category/list', data);
export const deleteCategory = data => fetch('/api/v1/category/delete', data);
export const detailCategory = data => fetch('/api/v1/category/detail', data);
export const updateCategory = data => fetch('/api/v1/category/update', data);
export const createCategory = data => fetch('/api/v1/category/create', data);

export const listMaterials = data => fetch('/api/v1/material/list', data);
export const deleteMaterial = data => fetch('/api/v1/material/delete', data);
export const detailMaterial = data => fetch('/api/v1/material/detail', data);
export const updateMaterial = data => fetch('/api/v1/material/update', data);
export const createMaterial = data => fetch('/api/v1/material/create', data);

export const allGrades = data => fetch('/api/v1/grade/all', data);
export const listGrades = data => fetch('/api/v1/grade/list', data);
export const deleteGrade = data => fetch('/api/v1/grade/delete', data);
export const detailGrade = data => fetch('/api/v1/grade/detail', data);
export const updateGrade = data => fetch('/api/v1/grade/update', data);
export const createGrade = data => fetch('/api/v1/grade/create', data);

export const listAccounts = data => fetch('/api/v1/account/list', data);
export const detailAccount = data => fetch('/api/v1/account/detail', data);
export const createAccount = data => fetch('/api/v1/account/create', data);
export const updateAccount = data => fetch('/api/v1/account/update', data);
export const updateAccountPwd = data => fetch('/api/v1/account/updatePwd', data);
export const deleteAccount = data => fetch('/api/v1/account/delete', data);
export const enableAccount = data => fetch('/api/v1/account/enable', data);
export const disableAccount = data => fetch('/api/v1/account/disable', data);
