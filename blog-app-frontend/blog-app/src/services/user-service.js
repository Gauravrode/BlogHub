import { myAxios } from "./helper";

export const signUp = (user) => {
    return myAxios
        .post("/api/users/register", user)
        .then((response) => {
            return response.data; 
        })
        .catch((error) => {
            console.error("Signup API Error:", error);
            throw error; 
        });
};

export const loginUser = (loginDetail) => {
    return myAxios
        .post('/auth/login', loginDetail)
        .then(response => response.data) 
        .catch(error => {
            console.error("Login API Error:", error);
            throw error; 
        });
};
