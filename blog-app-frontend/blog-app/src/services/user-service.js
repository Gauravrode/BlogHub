import { myAxios } from "./helper";

export const signUp = (user) => {
    return myAxios
        .post("/api/users/register", user)
        .then((response) => {
            return response.data; // Return API response data
        })
        .catch((error) => {
            console.error("Signup API Error:", error);
            throw error; // Throw error to be handled in the component
        });
};
