package pt.ist.ave.jzx.Instructions;

public class JRNC_D extends Instruction {

	public JRNC_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		if (!m_carryF) {
//			m_pc16 = add16(m_pc16, (byte) m_memory.read8(m_pc16) + 1);
//		} else {
//			inc16pc();
//		}
		if(!_cpu.getM_carryF()){
			_cpu.setM_pc16(_cpu.add16(_cpu.getM_pc16(),(byte)_cpu.getM_memory().read8(_cpu.getM_pc16()) + 1));
		}else{
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		if(_cpu.getM_carryF()){
//			m_tstates += 12;
			return 12;
		}else{
//			m_tstates += 7;
			return 7;
		}
	}

}
