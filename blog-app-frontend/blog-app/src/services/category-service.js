import { myAxios } from "./helper";

export const loadCategories = () => {
    const token = localStorage.getItem("token"); // Retrieve token from localStorage

    return myAxios
        .get('/api/categories/get', {
            headers: {
                Authorization: token ? `Bearer ${token}` : "", // Add token if available
            }
        })
        .then(response => response.data)
        .catch(error => {
            console.error("Category API Error:", error);
            throw error;
        });
};
