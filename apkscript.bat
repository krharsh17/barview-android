git config --global user.name "Raghav Awasthi"
git config --global user.email "raghavawasthi2014@gmail.com"

git clone --quiet --branch=apk https://RaghavAwasthi:$GITHUB_API_KEY@github.com/RaghavAwasthi/barview-android apk > /dev/null
git status
cp build/outputs/apk/android-debug.apk apk/test-android-debug.apk
cd apk
cp C:\projects\barview-android\app\build\outputs\apk\app-debug.apk ./test-android-debug.apk
git status
ls
git checkout --orphan temporary
git status

git add test-android-debug.apk
git commit -am "[Auto] Update Test Apk ($(date +%Y-%m-%d.%H:%M:%S))"
echo "commit Done"

git branch -D apk
git branch -m apk

git push origin apk --force --quiet > /dev/null
