cd ..

rmdir /s /q .\src\main\resources\dist
cd .\gui
pnpm build:prod && move .\dist ..\src\main\resources
pause
