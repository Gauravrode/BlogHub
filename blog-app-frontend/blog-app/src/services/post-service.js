import { myAxios, privateAxios } from "./helper"

export const creatPost=(postData)=>
{
    return privateAxios.post(`/api/user/${postData.userId}/category/${postData.categoryId}/posts`,postData).then(response=>response.data)
}; 

export const loadAllPost = (pageNumber = 0, pageSize = 5, sortBy = "postId", sortDir = "desc") => {
    return myAxios
      .get(`/api/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&sortDir=${sortDir}`)
      .then((response) => response.data)
      .catch(error => {
        console.error(" Error fetching posts:", error);
        throw error;
      });
};

  
