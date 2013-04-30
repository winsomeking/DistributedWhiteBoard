/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

/**
 *
 * 
 */
public class WhiteBoardLock {

    private String lockOwnerUserId = null;
    private boolean locked = false;
    WhiteBoardLock(String lockOwnerUserId ){
        this.lockOwnerUserId = lockOwnerUserId;
        
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockOwnerUserId() {
        return lockOwnerUserId;
    }

    public void setLockOwnerUserId(String lockOwnerUserId) {
        this.lockOwnerUserId = lockOwnerUserId;
    }


    
}
