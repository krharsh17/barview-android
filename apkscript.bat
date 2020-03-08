echo %APPVEYOR_REPO_BRANCH%
echo "Building a Pull Request"
git config --global user.name "Raghav Awasthi"
git config --global user.email "raghavawasthi2014@gmail.com"

git clone --quiet --branch=apk https://github.com/RaghavAwasthi/barview-android apk 
ls
cd apk

cp C:\projects\barview-android\app\build\outputs\apk\app-debug.apk ./test-android.apk
git status
ls
git checkout --orphan temporary
git status

git add test-android.apk
git commit -am "[Auto] Update Test Apk (%date% %time%)"
echo "commit Done"

git branch -D apk
git branch -m apk

git push origin apk --force --quiet 