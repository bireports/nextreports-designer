/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.designer.ui.sqleditor.syntax;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * A simple UndoManager that groups the Edits in each 0.5 second. If the time 
 * difference between the current undo and the last one is less than 0.5 secs,
 * then the two edits are compound.
 * 
 * @author Decebal Suiu
 */
public class CompoundUndoManager extends UndoManager {
    
    /**
     * Delay between consequtive edits in ms where edits are added together.
     * If the delay is greater than this, then separate undo operations are 
     * done, otherwise they are combined.
     */
    public static final int IDLE_DELAY_MS = 500;

    private long startMillis = 0;
    private CompoundEdit compoundEdit = null;

    public CompoundUndoManager() {
    }

    @Override
    public synchronized boolean addEdit(UndoableEdit anEdit) {
        long now = System.currentTimeMillis();
        if (compoundEdit == null) {
            compoundEdit = new CompoundEdit();
        }
        compoundEdit.addEdit(anEdit);
        if (now - startMillis > IDLE_DELAY_MS) {
            compoundEdit.end();
            super.addEdit(compoundEdit);
            compoundEdit = null;
        }
        startMillis = now;
        
        return true;
    }

    @Override
    public synchronized boolean canRedo() {
        commitCompound();
        return super.canRedo();
    }

    @Override
    public synchronized boolean canUndo() {
        commitCompound();
        return super.canUndo();
    }

    @Override
    public synchronized void discardAllEdits() {
        compoundEdit = null;
        super.discardAllEdits();
    }

    @Override
    public synchronized void redo() throws CannotRedoException {
        commitCompound();
        super.redo();
    }

    @Override
    public synchronized void undo() throws CannotUndoException {
        commitCompound();
        super.undo();
    }

    private void commitCompound() {
        if (compoundEdit != null) {
            compoundEdit.end();
            super.addEdit(compoundEdit);
            compoundEdit = null;
        }
    }
    
}
