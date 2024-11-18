import { loginPostAsync, logout } from '../slices/loginSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';

const useCustomLogin = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const loginState = useSelector((state) => state.loginSlice);

  const location = useLocation();

  const from = location.state?.from || { pathname: '/' };

  const isLogin = loginState.email ? true : false;

  const doLogin = async (loginParam) => {
    const action = await dispatch(loginPostAsync(loginParam));
    return action.payload;
  };

  const doLogout = () => dispatch(logout());

  const moveToPath = (path) => {
    navigate({ pathname: path }, { replace: true });
  };

  const redirectToLogin = () => {
    navigate('/member/login', {
      state: {
        from: {
          pathname: location.pathname,
          search: location.search,
        },
      },
      replace: true,
    });
  };

  const moveToFrom = () => {
    navigate(
      {
        pathname: from.pathname,
        search: from.search,
      },
      { replace: true },
    );
  };

  return {
    loginState,
    isLogin,
    doLogin,
    doLogout,
    moveToPath,
    redirectToLogin,
    moveToFrom,
  };
};

export default useCustomLogin;
