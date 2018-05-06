//import api from '@/config/api'
import fetch from '@/config/fetch'

//export const login = data => fetch('/admin/login', data, 'POST');

export const logout = data => fetch('/api/logout', data);
export const upload = data => fetch('/api/upload', data);

export const listCategories = data => fetch('/api/category/list', data);
export const deleteCategory = data => fetch('/api/category/delete', data);
export const detailCategory = data => fetch('/api/category/detail', data);
export const updateCategory = data => fetch('/api/category/update', data);
export const createCategory = data => fetch('/api/category/create', data);

export const listMaterials = data => fetch('/api/material/list', data);
export const deleteMaterial = data => fetch('/api/material/delete', data);
export const detailMaterial = data => fetch('/api/material/detail', data);
export const updateMaterial = data => fetch('/api/material/update', data);
export const createMaterial = data => fetch('/api/material/create', data);

export const allGrades = data => fetch('/api/grade/all', data);
export const listGrade = data => fetch('/api/grade/list', data);
export const deleteGrade = data => fetch('/api/grade/delete', data);
export const detailGrade = data => fetch('/api/grade/detail', data);
export const updateGrade = data => fetch('/api/grade/update', data);
export const createGrade = data => fetch('/api/grade/create', data);
