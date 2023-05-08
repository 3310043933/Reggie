function loginApi(param) {
    return $axios({
        'url': '/user/login',
        'method': 'post',
        data:{...param}
    })
}

function loginoutApi() {
    return $axios({
        'url': '/user/loginout',
        'method': 'post',
    })
}

  