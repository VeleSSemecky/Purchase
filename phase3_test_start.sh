#!/bin/zsh
# Quick Start Script for Phase 3 Testing
# Run this to install and launch the app on your emulator

echo "🚀 Phase 3 Testing - Quick Start"
echo "================================"
echo ""

cd /Users/yuriimelnyk/StudioProjects/Purchase

echo "📦 Building and installing APK..."
./gradlew :androidApp:installDebug

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Installation successful!"
    echo ""
    echo "🎬 Launching app..."
    adb shell am start -n com.example.androidapp/.MainActivity

    echo ""
    echo "════════════════════════════════════════════════════════"
    echo "✅ App launched!"
    echo ""
    echo "📋 Next steps:"
    echo "1. Test all 12 screens (see PHASE_3_TESTING_GUIDE.md)"
    echo "2. Document findings in PHASE_3_TESTING_RESULTS.md"
    echo "3. Take screenshots of each screen"
    echo ""
    echo "💡 To view logs:"
    echo "   adb logcat | grep -i purchase"
    echo ""
    echo "🎯 Testing focus:"
    echo "   - Navigation between screens"
    echo "   - Search functionality"
    echo "   - Swipe-to-delete (0.7f threshold)"
    echo "   - Mock data display"
    echo "   - UI components (SearchTopAppBar, SwipeToDismiss)"
    echo "════════════════════════════════════════════════════════"
else
    echo ""
    echo "❌ Installation failed. Check the error above."
    echo ""
    echo "Troubleshooting:"
    echo "1. Make sure emulator is running"
    echo "2. Check adb connection: adb devices"
    echo "3. Try manual install: ./gradlew :androidApp:assembleDebug"
fi

