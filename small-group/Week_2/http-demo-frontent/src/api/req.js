import axios from "axios";

//统一请求方法
let baseUrl = '';
//POST
export const postReq = (url,data) => {
    return axios({
        method: 'POST',
        url: `/api${baseUrl}${url}`,
        data: data
    })
}

//GET
export const getReq = (url,param) => {
    return axios({
        method: 'GET',
        url: `/api${baseUrl}${url}`,
        params: param
    })
}



//DELETE
export const deleteReq = (url,ids) => {
    return axios({
        method: 'DELETE',
        url: `/api${baseUrl}${url}/${ids}`
    })
}

//PUT
export const putReq = (url,data) => {
    return axios({
        method: 'PUT',
        url: `/api${baseUrl}${url}`,
        data: data
    })
}

export const patchReq = (url,data) => {
    return axios({
        method: 'PATCH',
        url: `/api${baseUrl}${url}`,
        data: data
    })
}