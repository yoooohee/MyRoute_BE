document.addEventListener('DOMContentLoaded', () => {
    // 회원가입 폼 요소 선택
    const signupForm = document.querySelector('.signup-form');
    const userIdInput = document.getElementById('userId');
    const checkIdButton = document.getElementById('checkIdButton');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('passwordConfirm');
    const passwordError = document.getElementById('passwordError');

    // 아이디 중복확인 버튼 클릭 이벤트
    checkIdButton.addEventListener('click', (e) => {
        e.preventDefault();
        const userId = userIdInput.value.trim();
        if (!userId) {
            alert('아이디를 입력해주세요.');
            return;
        }
        // 로컬스토리지에서 사용자 배열 가져오기 (없으면 빈 배열)
        let users = [];
        const usersStr = localStorage.getItem('users');
        if (usersStr) {
            users = JSON.parse(usersStr);
        }
        // 중복 여부 검사
        const duplicate = users.some((user) => user.id === userId);
        if (duplicate) {
            alert('이미 존재하는 아이디입니다.');
        } else {
            alert('사용 가능한 아이디입니다.');
        }
    });

    // 비밀번호 확인 입력란 실시간 검사
    passwordConfirmInput.addEventListener('input', () => {
        if (passwordInput.value !== passwordConfirmInput.value) {
            passwordError.style.display = 'block';
        } else {
            passwordError.style.display = 'none';
        }
    });

    // 회원가입 폼 제출 이벤트
    signupForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const userId = userIdInput.value.trim();
        const password = passwordInput.value;
        const passwordConfirm = passwordConfirmInput.value;

        // 비밀번호 일치 여부 검사
        if (password !== passwordConfirm) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        if (!userId || !password) {
            alert('아이디와 비밀번호를 모두 입력해주세요.');
            return;
        }

        // 로컬스토리지에서 기존 사용자 배열 가져오기 (없으면 빈 배열)
        let users = [];
        const usersStr = localStorage.getItem('users');
        if (usersStr) {
            users = JSON.parse(usersStr);
        }

        // 중복 아이디 검사 (추가 확인)
        const userExists = users.some((user) => user.id === userId);
        if (userExists) {
            alert('이미 존재하는 아이디입니다.');
            return;
        }

        // 새 사용자 객체 생성 (관리자 권한은 기본 false)
        const newUser = {
            id: userId,
            pw: password,
            isAdmin: false,
        };

        // 사용자 배열에 추가 후 로컬스토리지 업데이트
        users.push(newUser);
        localStorage.setItem('users', JSON.stringify(users));

        alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
        window.location.href = '../html/login.html';
    });
});
