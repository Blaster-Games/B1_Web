import { loginPostAsync, logout } from '../slices/loginSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const useCustomLogin = () => {
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const loginState = useSelector((state) => state.loginSlice);

  const isLogin = loginState.email ? true : false;

  const doLogin = async (loginParam) => {
    const action = dispatch(loginPostAsync(loginParam));
    return action.payload;
  };

  const doLogout = () => dispatch(logout());

  const moveToPath = (path) => {
    navigate({ pathname: path }, { replace: true });
  };

  const moveToLogin = () => {
    navigate({ pathname: '/member/login' }, { replace: true });
  };

  return { loginState, isLogin, doLogin, doLogout, moveToPath, moveToLogin };
};

export default useCustomLogin;
