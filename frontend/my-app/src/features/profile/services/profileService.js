import apiConst from '../../../../services/apiConst.js';

export const profileService = {
    getProfile: () => apiConst.get(`auth/me`)
};

export default profileService;