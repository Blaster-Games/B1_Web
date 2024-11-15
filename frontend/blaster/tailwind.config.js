/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      scrollbarBg: '#2d2d2d', // 스크롤바 배경색
      scrollbarThumb: '#4a5568', // 스크롤바 손잡이 색상
      scrollbarThumbHover: '#718096', // 호버 시 손잡이 색상
    },
  },
  plugins: [],
}