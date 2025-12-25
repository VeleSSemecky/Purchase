#!/bin/bash

# Complete Clean and Rebuild Script for iOS App
# This ensures Xcode uses the latest shared framework

set -e

PROJECT_DIR="/Users/yuriimelnyk/StudioProjects/Purchase"
XCODE_PROJECT="$PROJECT_DIR/iosApp/iosApp.xcodeproj"

echo "🧹 Step 1: Cleaning Gradle caches..."
cd "$PROJECT_DIR"
./gradlew clean cleanBuildCache

echo "🔨 Step 2: Rebuilding shared framework..."
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64 --rerun-tasks

echo "✅ Framework rebuilt!"
echo ""
echo "📱 Step 3: Clean Xcode (YOU MUST DO THIS IN XCODE):"
echo ""
echo "   In Xcode, press: ⌘⇧K (Command + Shift + K)"
echo "   OR: Product > Clean Build Folder"
echo ""
echo "   Then wait for 'Clean Finished' message"
echo ""
echo "📱 Step 4: Rebuild and Run in Xcode:"
echo ""
echo "   Press: ⌘R (Command + R)"
echo ""
echo "⚠️  IMPORTANT: You MUST clean Xcode's build folder!"
echo "    Xcode caches the old framework and won't use the new one otherwise."
echo ""
echo "Framework location:"
echo "$PROJECT_DIR/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework"
echo ""

