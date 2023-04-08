package net.floodlightcontroller.packetlogger;

import net.floodlightcontroller.core.*;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.DatapathId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SimplePacketLogger implements IFloodlightModule, IOFMessageListener {
    protected static Logger logger;
    protected IFloodlightProviderService floodlightProvider;


    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l =
                new ArrayList<Class<? extends IFloodlightService>>();
        l.add(IFloodlightProviderService.class);
        return l;
    }

    @Override
    public void init(FloodlightModuleContext context) {
        logger = LoggerFactory.getLogger(SimplePacketLogger.class);
        floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
    }

    @Override
    public void startUp(FloodlightModuleContext context) {
        logger.info("Vinit €€€€€€€€€ ******************* Simple Packet Logger module started.");
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
    }

    @Override
    public String getName() {
        return SimplePacketLogger.class.getSimpleName();
    }

    @Override
    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        return false;
    }

    @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        return false;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        return null;
    }

    @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
        return null;
    }

    @Override
    public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
        // Get the packet from the message context
        Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);

        // Log the packet to the console
        logger.info("********** Received packet: {}", eth);

        return Command.CONTINUE;
    }

    /**
     * Packet-in handler that logs all packets to the console.
     */
    /*public class SimplePacketLoggerSwitchListener implements IOFSwitchListener {

        @Override
        public void switchAdded(DatapathId switchId) {
            logger.info("******** Switch {} connected", switchId);

            // Register the packet-in handler with the switch
            //switchId.addPacketListener(new SimplePacketLoggerPacketListener());
        }

        @Override
        public void switchRemoved(DatapathId switchId) {
            logger.info("********* Switch {} disconnected", switchId);
        }

        @Override
        public void switchActivated(DatapathId switchId) {

        }

        @Override
        public void switchPortChanged(DatapathId switchId, OFPortDesc port, PortChangeType type) {

        }

        @Override
        public void switchChanged(DatapathId switchId) {

        }

        @Override
        public void switchDeactivated(DatapathId switchId) {

        }
    }*/

}
