#!/bin/bash

# Script to create Xcode project for iOS app
# This creates a proper Xcode project structure for the KMP iOS app

set -e

PROJECT_DIR="/Users/yuriimelnyk/StudioProjects/Purchase"
IOS_APP_DIR="$PROJECT_DIR/iosApp"
XCODE_PROJECT="$IOS_APP_DIR/iosApp.xcodeproj"

echo "Creating Xcode project for Purchase iOS app..."

# Navigate to iosApp directory
cd "$IOS_APP_DIR"

# Create the Xcode project using xcodeproj command
# We'll use a simple approach: open Xcode and create project manually,
# OR use this script to guide the manual creation

echo ""
echo "==========================================="
echo "iOS App Xcode Project Setup Instructions"
echo "==========================================="
echo ""
echo "Your iOS app files are ready, but you need to create an Xcode project."
echo "Here's how to do it:"
echo ""
echo "OPTION 1: Use Xcode GUI (Recommended)"
echo "--------------------------------------"
echo "1. Open Xcode"
echo "2. Click 'Create a new Xcode project'"
echo "3. Choose 'iOS' > 'App'"
echo "4. Click 'Next'"
echo "5. Fill in the details:"
echo "   - Product Name: iosApp"
echo "   - Team: (Select your team or leave as None)"
echo "   - Organization Identifier: com.veles.purchase"
echo "   - Bundle Identifier: com.veles.purchase.iosApp"
echo "   - Interface: SwiftUI"
echo "   - Language: Swift"
echo "   - Storage: None"
echo "   - Uncheck all checkboxes"
echo "6. Click 'Next'"
echo "7. Save it in: $IOS_APP_DIR"
echo "   IMPORTANT: Uncheck 'Create Git repository'"
echo "8. Click 'Create'"
echo ""
echo "9. After project is created:"
echo "   - Delete the default ContentView.swift and iOSAppApp.swift files"
echo "   - Drag and drop the existing files from iosApp/iosApp folder into the project"
echo "   - Add the shared.framework:"
echo "     a. Select the project in the navigator"
echo "     b. Select the iosApp target"
echo "     c. Go to 'General' tab"
echo "     d. Scroll to 'Frameworks, Libraries, and Embedded Content'"
echo "     e. Click '+' button"
echo "     f. Click 'Add Other...' > 'Add Files...'"
echo "     g. Navigate to: $PROJECT_DIR/shared/build/bin/iosSimulatorArm64/debugFramework/"
echo "     h. Select 'shared.framework'"
echo "     i. Make sure 'Embed & Sign' is selected"
echo ""
echo "10. Update Build Settings:"
echo "    - Select the project > Build Settings"
echo "    - Search for 'Framework Search Paths'"
echo "    - Add: \$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework"
echo ""
echo "11. Add Run Script Phase (to rebuild framework when needed):"
echo "    - Select the project > Build Phases"
echo "    - Click '+' > 'New Run Script Phase'"
echo "    - Drag it above 'Compile Sources'"
echo "    - Add this script:"
echo "    cd \"\$SRCROOT/..\""
echo "    ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64"
echo ""
echo "OPTION 2: Use our automated script (Advanced)"
echo "---------------------------------------------"
echo "Run: ./setup_ios_xcode.sh"
echo ""
echo "==========================================="

# Check if project already exists
if [ -d "$XCODE_PROJECT" ]; then
    echo ""
    echo "✓ Xcode project already exists at: $XCODE_PROJECT"
    echo "You can open it with: open $XCODE_PROJECT"
else
    echo ""
    echo "⚠ Xcode project does not exist yet."
    echo "Please follow the instructions above to create it."
fi

echo ""

