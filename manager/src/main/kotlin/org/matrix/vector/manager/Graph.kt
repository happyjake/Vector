package org.matrix.vector.manager

import org.lsposed.lspd.ILSPManagerService
import org.matrix.vector.manager.ipc.DaemonClient
import org.matrix.vector.manager.data.repository.ModuleRepository

/**
 * A lightweight Dependency Injection container.
 * This holds our singleton instances, where Constants.setBinder(binder) will initialize the DaemonClient.
 */
object Graph {
    // Initialized immediately to prevent UninitializedPropertyAccessException
    val daemonClient = DaemonClient()
    val moduleRepository = ModuleRepository(daemonClient)

    var service: ILSPManagerService? = null 
        set(value) {
            field = value
            daemonClient.service = value
            // If the daemon connects, immediately tell the repository to fetch modules
            if (value != null) {
                moduleRepository.refreshEnabledModules()
            }
        }
}
