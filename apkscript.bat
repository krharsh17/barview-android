git config --global user.name "Raghav Awasthi"
git config --global user.email "raghavawasthi2014@gmail.com"

git clone --quiet --branch=apk https://RaghavAwasthi:$GITHUB_API_KEY@github.com/RaghavAwasthi/barview-android apk > /dev/null
cp platforms/android/build/outputs/apk/android-debug.apk apk/test-android-debug.apk
cd apk
cp C:\projects\barview-android\app\build\outputs\apk\app-debug.apk ./test-android-debug.apk
# Create a new branch that will contains only latest apk
git checkout --orphan temporary

# Add generated APK
git add test-android-debug.apk
git commit -am "[Auto] Update Test Apk ($(date +%Y-%m-%d.%H:%M:%S))"

# Delete current apk branch
git branch -D apk
# Rename current branch to apk
git branch -m apk

# Force push to origin since histories are unrelated
git push origin apk --force --quiet > /dev/null
