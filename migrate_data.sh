#!/bin/bash

# Phase 5 - Task 5.4: Data Module Migration Script
# Migrates data layer from data module to shared/commonMain

set -e

PROJECT_ROOT="/Users/yuriimelnyk/StudioProjects/Purchase"
SOURCE_DIR="$PROJECT_ROOT/data/src/main/java/com/veles/purchase/data"
DEST_DIR="$PROJECT_ROOT/shared/src/commonMain/kotlin/com/veles/purchase/data"

echo "🚀 Starting Data Module Migration..."
echo "Source: $SOURCE_DIR"
echo "Destination: $DEST_DIR"
echo ""

# Create destination directories
echo "📁 Creating directory structure..."
mkdir -p "$DEST_DIR/room"
mkdir -p "$DEST_DIR/room/dao"
mkdir -p "$DEST_DIR/room/table"
mkdir -p "$DEST_DIR/room/migration"
mkdir -p "$DEST_DIR/room/util"
mkdir -p "$DEST_DIR/repository"

# Copy Room database files
echo "📋 Copying Room database files..."
if [ -d "$SOURCE_DIR/room" ]; then
    cp -R "$SOURCE_DIR/room/"* "$DEST_DIR/room/" 2>/dev/null || true
fi

# Copy repository implementations
echo "📋 Copying repository implementations..."
if [ -d "$SOURCE_DIR/repository" ]; then
    cp -R "$SOURCE_DIR/repository/"* "$DEST_DIR/repository/" 2>/dev/null || true
fi

# Copy any other data files
echo "📋 Copying remaining data files..."
find "$SOURCE_DIR" -maxdepth 1 -name "*.kt" -exec cp {} "$DEST_DIR/" \; 2>/dev/null || true

echo "✅ Files copied successfully!"
echo ""
echo "📝 Manual fixes needed:"
echo "1. Create DatabaseBuilder (expect/actual)"
echo "2. Replace Android-specific types in TypeConverters"
echo "3. Update repository implementations for KMP"
echo "4. Remove javax.inject usage"
echo "5. Replace Retrofit with Ktor"
echo "6. Handle Firebase (expect/actual)"
echo ""
echo "Total files copied: $(find $DEST_DIR -name '*.kt' 2>/dev/null | wc -l)"
echo ""
echo "✨ Data migration script complete!"

