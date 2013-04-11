package pt.ist.ave.jzx.Instructions;

public class JRNC_D extends Instruction {

	private int _tstates;

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
			_tstates = 12;
			_cpu.setM_pc16(_cpu.add16(_cpu.getM_pc16(),(byte)_cpu.getM_memory().read8(_cpu.getM_pc16()) + 1));
		}else{
			_tstates = 7;
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}
}


