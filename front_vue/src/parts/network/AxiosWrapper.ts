import axios from 'axios';

axios.defaults.headers.common['If-Modified-Since'] =
  'Thu, 01 Jun 1970 00:00:00 GMT';
axios.interceptors.response.use(
  response => {
    if (response.headers['x-amz-meta-maintenance'])
      location.href = window.location.protocol + '//' + window.location.host;
    return response;
  },
  error => {
    return Promise.reject(error);
  }
);

export default axios;
